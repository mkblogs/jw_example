package com.tech.mkblogs.sequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.tech.mkblogs.model.AccountSequence;

@Service
public class SequenceGeneratorService {

	@Autowired
	private MongoOperations mongoOperations;

    public Integer generateSequence(String seqName) {
    	AccountSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                   new Update().inc("sequence",1), options().returnNew(true).upsert(true),
                   AccountSequence.class);
        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }
}
