import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VectorSpaceModelTest {

    @Test
    public void test() {
        String doc = "Here lies Dobby, a free elf, elf ,elf";
        Corpus corpus = new Corpus();
        VectorSpaceModel model = new VectorSpaceModel(corpus);
        corpus.parseDoc(doc);

        // check documents
        assertEquals(1, corpus.documents.size());
        // check allTermFreq
        assertEquals(1, corpus.allTermFreq.size());
        assertEquals(6, corpus.allTermFreq.get(0).keySet().size());
        assertEquals(1, (int)corpus.allTermFreq.get(0).get("here"));
        assertEquals(1, (int)corpus.allTermFreq.get(0).get("lies"));
        assertEquals(1, (int)corpus.allTermFreq.get(0).get("dobby"));
        assertEquals(1, (int)corpus.allTermFreq.get(0).get("a"));
        assertEquals(1, (int)corpus.allTermFreq.get(0).get("free"));
        assertEquals(3, (int)corpus.allTermFreq.get(0).get("elf"));

        // check
        assertEquals(6, corpus.docFreq.keySet().size());
        assertEquals(1, (int)corpus.docFreq.get("here"));
        assertEquals(1, (int)corpus.docFreq.get("lies"));
        assertEquals(1, (int)corpus.docFreq.get("dobby"));
        assertEquals(1, (int)corpus.docFreq.get("a"));
        assertEquals(1, (int)corpus.docFreq.get("free"));
        assertEquals(1, (int)corpus.docFreq.get("elf"));

        String doc2 = "A house elf must be set free, sir. And the family will never set Dobby free"
                    + " ... Dobby will serve the family until he dies, sir";
        corpus.parseDoc(doc2);
        // check documents
        assertEquals(2, corpus.documents.size());
        // check allTermFreq
        assertEquals(2, corpus.allTermFreq.size());
        assertEquals(6, corpus.allTermFreq.get(0).keySet().size());
        assertEquals(18, corpus.allTermFreq.get(1).keySet().size());
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("a"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("house"));
        assertEquals(2, (int)corpus.allTermFreq.get(1).get("dobby"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("elf"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("must"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("be"));
        assertEquals(2, (int)corpus.allTermFreq.get(1).get("set"));
        assertEquals(2, (int)corpus.allTermFreq.get(1).get("free"));

        assertEquals(2, (int)corpus.allTermFreq.get(1).get("sir"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("and"));
        assertEquals(2, (int)corpus.allTermFreq.get(1).get("the"));
        assertEquals(2, (int)corpus.allTermFreq.get(1).get("family"));
        assertEquals(2, (int)corpus.allTermFreq.get(1).get("will"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("never"));

        assertEquals(1, (int)corpus.allTermFreq.get(1).get("serve"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("until"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("he"));
        assertEquals(1, (int)corpus.allTermFreq.get(1).get("dies"));

        // check docFreq
        assertEquals(20, corpus.docFreq.keySet().size());
        assertEquals(2, (int)corpus.docFreq.get("a"));
        assertEquals(1, (int)corpus.docFreq.get("here"));
        assertEquals(1, (int)corpus.docFreq.get("lies"));
        assertEquals(2, (int)corpus.docFreq.get("dobby"));
        assertEquals(2, (int)corpus.docFreq.get("free"));
        assertEquals(2, (int)corpus.docFreq.get("elf"));
        assertEquals(1, (int)corpus.docFreq.get("house"));
        assertEquals(1, (int)corpus.docFreq.get("must"));
        assertEquals(1, (int)corpus.docFreq.get("be"));
        assertEquals(1, (int)corpus.docFreq.get("set"));
        assertEquals(1, (int)corpus.docFreq.get("sir"));
        assertEquals(1, (int)corpus.docFreq.get("and"));
        assertEquals(1, (int)corpus.docFreq.get("the"));
        assertEquals(1, (int)corpus.docFreq.get("family"));
        assertEquals(1, (int)corpus.docFreq.get("will"));
        assertEquals(1, (int)corpus.docFreq.get("never"));
        assertEquals(1, (int)corpus.docFreq.get("serve"));
        assertEquals(1, (int)corpus.docFreq.get("until"));
        assertEquals(1, (int)corpus.docFreq.get("he"));
        assertEquals(1, (int)corpus.docFreq.get("dies"));
    }
}
