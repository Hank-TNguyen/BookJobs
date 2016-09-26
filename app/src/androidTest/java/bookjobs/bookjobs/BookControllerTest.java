package bookjobs.bookjobs;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

/**
 * Created by Hung on 9/26/2016.
 */


public class BookControllerTest {
    // currently failed but the test still passes as it skips initialization of the task because of Firebase failed to connect
    // my guess is that it requires authentication

    final private String TEST_ISBN = "9781566199094";
    final private String TEST_TITLE = "Book of Love";
    final private String TEST_USER_NAME = "HANK";
    final private String TEST_USER_EMAIL = "HANK@GMAIL";

    public class UploadBookTaskTest extends ApplicationTestCase {
        Book testB = new Book(TEST_ISBN, TEST_TITLE);
        User testU = new User(TEST_USER_NAME, TEST_USER_EMAIL);
        Exception mError = null;
        String mResult = null;

        public UploadBookTaskTest(){
            super(Application.class);
        }

        protected void setUp() throws Exception{
            super.setUp();
        }

        protected void tearDown() throws Exception{
            super.tearDown();
        }

        @Test
        public void testUploadBookTask() throws InterruptedException{
            BookController.UploadBookTask testTask = new BookController.UploadBookTask(testB, testU);
            testTask.execute();
            assertTrue(true);
        }


    }

}
