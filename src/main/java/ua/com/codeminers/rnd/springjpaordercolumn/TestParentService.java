package ua.com.codeminers.rnd.springjpaordercolumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

@Service
public class TestParentService {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TestParentRepositoty testParentRepositoty;

    @Autowired
    private TestChildRepositoty testChildRepositoty;

    @Transactional
    public void clear() {
        testChildRepositoty.deleteAll();
        testParentRepositoty.deleteAll();
    }

    @Transactional
    public void addEntities() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                log.info(">> addEntities transaction completed");
            }
        });

        log.info(">> Starting init");

        TestParent parent1 = new TestParent();
        parent1.setDetails("Parent1");

        TestParent parent2 = new TestParent();
        parent2.setDetails("Parent2");

        //List<TestParent> parents = Arrays.asList(parent);

        TestChild child1 = new TestChild();
        child1.setDetails("Child1");
        //child1.setParents(parents);

        TestChild child2 = new TestChild();
        child2.setDetails("Child2");
        //child2.setParents(parents);

        testChildRepositoty.save(child1);
        testChildRepositoty.save(child2);

        parent1.setChilds(Arrays.asList(child1));
        parent2.setChilds(Arrays.asList(child1, child2));
        testParentRepositoty.save(parent1);
        testParentRepositoty.save(parent2);

        log.info(">> Inited");
    }

    @Transactional
    public void addChild() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                log.info(">> Adding child transaction completed");
            }
        });

        log.info(">> Adding child");

        TestParent parent = testParentRepositoty.findAll().get(0);

        TestChild child3 = new TestChild();
        child3.setDetails("Child3");
        //child3.setParents(parent.getChilds());
        
        parent.getChilds().add(child3);
        // parent.getChilds().add(1, child3);

        testChildRepositoty.save(child3);
        testParentRepositoty.save(parent);

        log.info(">> Added child");
    }

    @Transactional
    public void removeChild() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                log.info(">> Removing child transaction completed");
            }
        });

        log.info(">> Removing child");

        TestParent parent = testParentRepositoty.findAll().get(1);

        //TestChild child3 = new TestChild();
        //child3.setDetails("Child3");
        //child3.setParents(parent.getChilds());
        
        parent.getChilds().removeAll(parent.getChilds());
        // parent.getChilds().add(1, child3);

        //testChildRepositoty.save(child3);
        testParentRepositoty.save(parent);

        log.info(">> Child removed");
    }

//    @Transactional
//    public void addChild2(String details, boolean throwError) {
//        self.addChild(details, throwError);
//        if (throwError) {
//            throw new RuntimeException();
//        }        
//    }
//
//    //@Transactional//(propagation = Propagation.REQUIRES_NEW)//(noRollbackFor = {RuntimeException.class})
//    public void addChildWithExceptionHandling0() {
//        if (TransactionSynchronizationManager.isSynchronizationActive()) {
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                @Override
//                public void afterCompletion(int status) {
//                    log.info(">> addChildWithExceptionHandling transaction completed with status {}", status);
//                }
//            });
//        }
//
//        log.info(">> Begin addChildWithExceptionHandling");
//
//        try {
//            //self.addChild("First", true);
//            //throw new RuntimeException();
//            self.throwError();
//        } catch (Exception e) {
//            log.info(">> Catch exception");
//            //self.addChild("Second", false);
//            self.handleError();
//        }
//
//        log.info(">> End addChildWithExceptionHandling");
//    }
//    
//    @Transactional//(propagation = Propagation.REQUIRES_NEW)
//    public void throwError() {
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//            @Override
//            public void afterCompletion(int status) {
//                log.info(">> throwError transaction completed with status {}", status);
//            }
//        });
//
//        throw new RuntimeException();
//    }
//
//    @Transactional
//    public void someTransaction() {
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//            @Override
//            public void afterCompletion(int status) {
//                log.info(">> someTransaction transaction completed with status {}", status);
//            }
//        });
//    }
//
//    //@Transactional
//    private void handleError() {
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//            @Override
//            public void afterCompletion(int status) {
//                log.info(">> handleError transaction completed with status {}", status);
//            }
//        });
//    }
//    
//    //@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
//    //(propagation = Propagation.REQUIRES_NEW)//(noRollbackFor = {RuntimeException.class})
//    //(rollbackFor = RuntimeException.class)
//    public void addChildWithExceptionHandling() {
//        if (TransactionSynchronizationManager.isSynchronizationActive()) {
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                @Override
//                public void afterCompletion(int status) {
//                    log.info(">> addChildWithExceptionHandling transaction completed with status {}", status);
//                }
//            });
//        }
//
//        log.info(">> Begin addChildWithExceptionHandling");
//
//        try {
//            newTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                public void doInTransactionWithoutResult(TransactionStatus status) {
//                    self.someTransaction();
//                    self.throwError();
//                }
//            });
//        } catch (Exception e) {
//            log.info(">> Catch exception");
//            newTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                public void doInTransactionWithoutResult(TransactionStatus status) {
//                    handleError();
//                    throw new RuntimeException("XXXXXXXXX");
//                }
//            });
//        }
//
//        log.info(">> End addChildWithExceptionHandling");
//        //throw new RuntimeException();
//    }

}
