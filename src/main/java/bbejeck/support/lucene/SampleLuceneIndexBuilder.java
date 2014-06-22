package bbejeck.support.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SampleLuceneIndexBuilder {

    private String namesFile;

    public SampleLuceneIndexBuilder(String namesFile) {
        this.namesFile = namesFile;
    }

    public RAMDirectory buildIndex() throws IOException {
        RAMDirectory ramDirectory = new RAMDirectory();
        Document doc = new Document();

        Field[] fields = new Field[]{
                new TextField("firstName", "", Field.Store.NO),
                new TextField("lastName", "", Field.Store.NO),
                new TextField("address", "", Field.Store.NO),
                new TextField("email", "", Field.Store.NO),
                new TextField("id", "", Field.Store.YES)
        };

        addFieldsToDocument(doc, fields);

        BufferedReader reader = new BufferedReader(new FileReader(namesFile));

        IndexWriter indexWriter = new IndexWriter(ramDirectory, new IndexWriterConfig(Version.LUCENE_48, new StandardAnalyzer(Version.LUCENE_48)));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] personData = getPersonData(line);
            setFieldData(personData, fields);
            indexWriter.addDocument(doc);
        }
        indexWriter.close();
        return ramDirectory;
    }

    private  String[] getPersonData(String line) {
        return line.split(",");
    }

    private  void setFieldData(String[] data, Field[] fields) {
        int index = 0;
        for (Field field : fields) {
            field.setStringValue(data[index++]);
        }
    }

    private  void addFieldsToDocument(Document doc, Field[] fields) {
        for (Field field : fields) {
            doc.add(field);
        }
    }

}
