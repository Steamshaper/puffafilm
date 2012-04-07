package org.steamshaper.ai.puffafilm.etl.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.steamshaper.ai.puffafilm.etl.entity.ETag;
import org.steamshaper.ai.puffafilm.etl.test.ALoaderTester;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.puffafilm.ai.node.GNTag;
import org.steamshaper.puffafilm.ai.repository.GNTagsRepository;

public class TagsLoaderTest extends ALoaderTester {

    @Test
    public void testConvertETag() {
    	Help.me.toCleanN4JDatabase();
        ETag src = generateTagObj();
        TagsLoader tested = new TagsLoader();
        GNTag o = tested.convert(src);
        assertEquals(src.getId(), o.getOid());
        assertEquals(src.getValue(), o.getName());

    }

    @Test
    public void testGetRepository() {
    	Help.me.toCleanN4JDatabase();
        TagsLoader tested = new TagsLoader();
        if (tested.getRepository() == null
                || !(tested.getRepository() instanceof GraphRepository<?>)) {
            fail("Repo not found");
        }
    }

//	@Autowired
//	GNTagsRepository repo;

    @Test
    public void testLoadWithRepo() {
    	Help.me.toCleanN4JDatabase();
        GNTagsRepository repo = Help.me.getContext().getBean(GNTagsRepository.class);
        TagsLoader tl = new TagsLoader();
        Transaction tx = Help.me.getNeo4jTemplate().beginTx();
        try{
        	tl.load(generateTagObj());
        	tx.success();
        }catch(Exception e){
        	tx.failure();
        }finally{
        tx.finish();
        }
        GNTag result = repo.findByPropertyValue("oid", 1L);
        if (result == null) {
            fail("No object found! ???");
        }
        if (result.getOid() != 1L || !"puffafilm".equals(result.getName())) {
            fail("The object returned don't match with the inserted!");
        }
    }

    private ETag generateTagObj() {
        ETag source = new ETag();
        source.setId(1L);
        source.setValue("puffafilm");

        return source;
    }
}
