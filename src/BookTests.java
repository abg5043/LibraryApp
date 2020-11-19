import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class BookTests {

    @Test public void testBookConstructor1() {
        Book b1 = new Book("dumb book");

    }
    @Test public void testBookConstructor2() {
        // Book b1 = new Book("dumb book", "john");
        //   Assert.assertEquals(b1.getRenter(), "john");

    }
    @Test public void testGetName() {
        Book b1 = new Book("dumb book");
        Assert.assertEquals(b1.getName(), "dumb book");
        Book b2 = new Book("dumber book");
        Assert.assertEquals(b2.getName(), "dumber book");
        Assert.assertEquals(b1.getName(), "dumb book");
    }
    @Test public void testRent() {
        Book b2 = new Book("dumber book");
        Book b1 = new Book("dumb book");
        Assert.assertNull(b1.getRenter());
        Assert.assertNull(b2.getRenter());
        Assert.assertNull(b1.getDueDate());
        Assert.assertNull(b2.getDueDate());
        b1.rent("jorge");
        Assert.assertEquals("jorge", b1.getRenter() );
        Assert.assertNull(b2.getRenter());
        Assert.assertEquals(b1.getDueDate(), LocalDate.now().plusDays(7));
        Assert.assertNull(b2.getDueDate());


    }
    @Test public void testReturn() {
        Book b2 = new Book("dumber book");
        Book b1 = new Book("dumb book");
        Assert.assertNull(b1.getRenter());
        Assert.assertNull(b2.getRenter());
        b1.rent("jorge");
        Assert.assertEquals("jorge",b1.getRenter());
        Assert.assertNull(b2.getRenter());
        b1.returnToLibrary();
        Assert.assertNull(b1.getRenter());
        Assert.assertNull(b2.getRenter());

    }

    @Test public void testCanRent() {
        Video video = new Video("My Neighbor Totoro", Rating.G);
        Assert.assertEquals(video.canRent(8), true);
    }



}