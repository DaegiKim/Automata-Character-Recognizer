package kr.ac.inha.project.automata.lucene;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 루씬 인덱스를 생성하는 클래스.
 * data 디렉터리에 포함된 모든 문서에 대한 인덱스를 생성한다.
 * 생성된 인덱스는 index 디렉터리에 저장됨.
 * @author DaegiKim
 *
 */

public class Indexer {
	public static void main(String[] args) throws IOException {
		String indexPath = "index";
		String docsPath = "data";

		final File docDir = new File(docsPath);

		Directory dir = FSDirectory.open(new File(indexPath));
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40,
				analyzer);

		IndexWriter writer = new IndexWriter(dir, iwc);
		indexDocs(writer, docDir);

		writer.forceMerge(1);
		writer.close();
	}

	public static void indexDocs(IndexWriter writer, File file)
			throws IOException {
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {
				Scanner filescan1;
				FileInputStream fis;
				try {
					filescan1 = new Scanner(file);
					fis = new FileInputStream(file);
				} catch (FileNotFoundException fnfe) {
					return;
				}

				try {
					while(filescan1.hasNext()){
						Document doc = new Document();
						
						String str = filescan1.next();
						String hangul = str.substring(0, 1);
						String unicode = str.substring(1, str.length());
						doc.add(new StringField("hangul", hangul, Store.YES));
						doc.add(new StringField("unicode", unicode, Store.YES));
						
						if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
							System.out.println("adding " + file);
							writer.addDocument(doc);
						} else {
							System.out.println("updating " + file);
							writer.updateDocument(new Term("path", file.getPath()),
									doc);
						}
					}
				} finally {
					fis.close();
					filescan1.close();
				}
			}
		}
	}
}
