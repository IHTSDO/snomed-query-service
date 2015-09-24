package com.kaicube.snomed.srqs;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReleaseReader {

	private final IndexSearcher isearcher;
	private final QueryParser parser;

	public ReleaseReader(ReleaseStore releaseStore) throws IOException {
		DirectoryReader ireader = DirectoryReader.open(releaseStore.getDirectory());
		isearcher = new IndexSearcher(ireader);
		final Analyzer analyzer = releaseStore.createAnalyzer();
		parser = new QueryParser(Version.LUCENE_40, "id", analyzer);
		parser.setAllowLeadingWildcard(true);
	}

	public long getConceptCount() throws IOException {
		return isearcher.collectionStatistics("id").docCount();
	}

	public List<String> retrieveConcepts(int limit) throws ParseException, IOException {
		List<String> concepts = new ArrayList<>();
		final Query query = parser.parse("*");
		final TopDocs topDocs = isearcher.search(query, limit);
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			concepts.add(isearcher.doc(scoreDoc.doc).get("id"));
		}
		return concepts;
	}
}
