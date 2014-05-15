package kr.ac.inha.project.automata.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 루씬 인덱스에서 유니코드를 검색하여 한글 스트링으로 반환해주는 클래스.
 * @author DaegiKim
 *
 */

public class Searcher {
	private String index = null;
	private IndexReader reader = null;
	private IndexSearcher searcher = null;
	private Analyzer analyzer = null;
	private QueryParser parser = null;
	private Query query = null;
	private TopDocs results = null;
	
	public Searcher() throws IOException {
		// TODO Auto-generated constructor stub
		index = "index";
		reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		searcher = new IndexSearcher(reader);
		analyzer = new StandardAnalyzer(Version.LUCENE_40);
		parser = new QueryParser(Version.LUCENE_40, "unicode", analyzer);
	}
	
	public String Searching(String unicode) throws ParseException, IOException{
		String hangul = "";
		query = parser.parse(unicode);
		results = searcher.search(query, 1);
		
		if(results.totalHits>0) {
			ScoreDoc[] hits = results.scoreDocs;
			Document doc = searcher.doc(hits[0].doc);
			hangul = doc.get("hangul");
		}
		return hangul;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		reader.close();
	}
}
