package org.ihtsdo.otf.sqs.service.store;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;

import java.io.IOException;

public interface ReleaseStore {

	public Analyzer createAnalyzer();

	public Directory getDirectory();

	public boolean isIndexExisting();

	public void destroy() throws IOException;
}
