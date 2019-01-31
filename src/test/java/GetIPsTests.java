import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class GetIPsTests
{
    @Test
    public void whenIPIsJustOne() throws Exception
    {
      GetIPs getIPs = new GetIPs("1.1.1.9","1.1.1.11");
        List<String> actual = getIPs.getBetweenIPs(getIPs.getIP1(),getIPs.getIP2());
        Assert.assertEquals("1.1.1.10", actual.get(0));
    }
    @Test
    public void whenIPIsZero() throws Exception
    {
        GetIPs getIPs = new GetIPs("1.1.1.10","1.1.1.11");
        List<String> actual = getIPs.getBetweenIPs(getIPs.getIP1(),getIPs.getIP2());
        Assert.assertEquals(0, actual.size());
    }
    @Test
    public void whenIPresultIsZeroAgain() throws Exception
    {
        GetIPs getIPs = new GetIPs("1.1.1.11","1.1.1.11");
        List<String> actual = getIPs.getBetweenIPs(getIPs.getIP1(),getIPs.getIP2());
        Assert.assertEquals(0, actual.size());
    }
    @Test
    public void whenIPsDiffersByTwoDigits() throws Exception
    {
        GetIPs getIPs = new GetIPs("1.1.3.10","1.1.2.250");
        List<String> actual = getIPs.getBetweenIPs(getIPs.getIP1(),getIPs.getIP2());
        Assert.assertEquals(15, actual.size());
    }
    @Test
    public void whenIPsDiffersByThreeDigits() throws Exception
    {
        GetIPs getIPs = new GetIPs("1.2.3.10","1.1.2.250");
        List<String> actual = getIPs.getBetweenIPs(getIPs.getIP1(),getIPs.getIP2());
        Assert.assertEquals(65807, actual.size());
    }

}
