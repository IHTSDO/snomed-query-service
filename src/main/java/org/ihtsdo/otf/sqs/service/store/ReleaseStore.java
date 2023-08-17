package org.ihtsdo.otf.sqs.service.store;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;

import java.io.IOException;

public interface ReleaseStore {

	Analyzer createAnalyzer();

	Directory getDirectory();

	boolean isIndexExisting();

	void destroy() throws IOException;
}
