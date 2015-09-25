package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.exceptions.NotFoundException;
import com.kaicube.snomed.srqs.parser.secl.ExpressionConstraintBaseListener;
import com.kaicube.snomed.srqs.parser.secl.ExpressionConstraintLexer;
import com.kaicube.snomed.srqs.parser.secl.ExpressionConstraintParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReleaseReader {

	private final IndexSearcher indexSearcher;
	private final QueryParser parser;

	public ReleaseReader(ReleaseStore releaseStore) throws IOException {
		indexSearcher = new IndexSearcher(DirectoryReader.open(releaseStore.getDirectory()));
		final Analyzer analyzer = releaseStore.createAnalyzer();
		parser = new QueryParser(Version.LUCENE_40, Concept.ID, analyzer);
		parser.setAllowLeadingWildcard(true);
	}

	protected ReleaseReader() {
		indexSearcher = null;
		parser = null;
	}

	public long getConceptCount() throws IOException {
		return indexSearcher.collectionStatistics(Concept.ID).docCount();
	}

	public List<String> retrieveConcepts(String ecQuery, int limit) throws ParseException, IOException, NotFoundException {
		List<String> concepts = new ArrayList<>();

		if (ecQuery != null && !ecQuery.isEmpty()) {
			final ExpressionConstraintListener listener = parseQuery(ecQuery);
			final String focusConcept = listener.focusConcept;
			if (listener.descendantOf) {
			 	concepts.addAll(retrieveConceptDescendants(focusConcept));
			} else if (listener.ancestorOf) {
				Collections.addAll(concepts, retrieveConceptAncestors(focusConcept));
			}
			if (listener.includeSelf) {
				concepts.add(0, getConceptDoc(focusConcept).get(Concept.ID));
			}
		} else {
			final TopDocs topDocs = indexSearcher.search(this.parser.parse("*"), limit);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				concepts.add(getId(scoreDoc));
			}
		}
		return concepts;
	}

	protected ExpressionConstraintListener parseQuery(String ecQuery) {
		final ExpressionConstraintLexer lexer = new ExpressionConstraintLexer(new ANTLRInputStream(ecQuery));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		final ExpressionConstraintParser parser = new ExpressionConstraintParser(tokens);
		ParserRuleContext tree = parser.expressionconstraint();

		final ParseTreeWalker walker = new ParseTreeWalker();
		final ExpressionConstraintListener listener = new ExpressionConstraintListener();
		walker.walk(listener, tree);
		return listener;
	}

	public String[] retrieveConceptAncestors(String conceptId) throws ParseException, IOException, NotFoundException {
		return getConceptDoc(conceptId).getValues(Concept.ANCESTOR);
	}

	public List<String> retrieveConceptDescendants(String conceptId) throws ParseException, IOException {
		List<String> concepts = new ArrayList<>();
		final Long idLong = new Long(conceptId);
		final TopDocs topDocs = indexSearcher.search(NumericRangeQuery.newLongRange(Concept.ANCESTOR, idLong, idLong, true, true), Integer.MAX_VALUE);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			concepts.add(getId(scoreDoc));
		}
		return concepts;
	}

	private String getId(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc).get(Concept.ID);
	}

	private Document getConceptDoc(String conceptId) throws IOException, NotFoundException {
		final Long idLong = new Long(conceptId);
		final TopDocs docs = indexSearcher.search(NumericRangeQuery.newLongRange(Concept.ID, idLong, idLong, true, true), 1);
		if (docs.totalHits < 1) {
			throw new NotFoundException("Concept with id " + conceptId + " could not be found.");
		}
		return indexSearcher.doc(docs.scoreDocs[0].doc);
	}

	protected static final class ExpressionConstraintListener extends ExpressionConstraintBaseListener {

		protected String focusConcept;
		protected boolean descendantOf;
		protected boolean ancestorOf;
		protected boolean includeSelf = true;

		@Override
		public void enterRefinedexpressionconstraint(ExpressionConstraintParser.RefinedexpressionconstraintContext ctx) {
			throwUnsupported();
		}

		@Override
		public void enterCompoundexpressionconstraint(ExpressionConstraintParser.CompoundexpressionconstraintContext ctx) {
			throwUnsupported();
		}

		@Override
		public void enterMemberof(ExpressionConstraintParser.MemberofContext ctx) {
			throwUnsupported("memberOf");
		}

		@Override
		public void enterWildcard(ExpressionConstraintParser.WildcardContext ctx) {
			throwUnsupported("wildcard");
		}

		@Override
		public void enterFocusconcept(ExpressionConstraintParser.FocusconceptContext ctx) {
			focusConcept = ctx.conceptreference().conceptid().getPayload().getText();
		}

		@Override
		public void enterDescendantof(ExpressionConstraintParser.DescendantofContext ctx) {
			descendantOf = true;
			includeSelf = false;
		}

		@Override
		public void enterDescendantorselfof(ExpressionConstraintParser.DescendantorselfofContext ctx) {
			descendantOf = true;
		}

		@Override
		public void enterAncestorof(ExpressionConstraintParser.AncestorofContext ctx) {
			ancestorOf = true;
			includeSelf = false;
		}

		@Override
		public void enterAncestororselfof(ExpressionConstraintParser.AncestororselfofContext ctx) {
			ancestorOf = true;
		}

		private UnsupportedOperationException throwUnsupported() {
			throw new UnsupportedOperationException("This expression is not currently supported, please use a simpleExpressionConstraint.");
		}

		private void throwUnsupported(String feature) {
			throw new UnsupportedOperationException(feature + " is not currently supported.");
		}
	}
}
