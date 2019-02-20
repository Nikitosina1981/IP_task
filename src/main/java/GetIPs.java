import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GetIPs
{
    private int[] IP1;
    private int[] IP2;

    public int[] getIP1()
    {
        return IP1;
    }

    public int[] getIP2()
    {
        return IP2;
    }

    GetIPs() // Enter 2 String valid IP address and print all that between
    {
        IP1 = enterIP();
        IP2 = enterIP();
        getBetweenIPs(IP1, IP2).forEach(System.out::println);
    }


    int[] enterIP() // Enter String, call validate IP and convert methods, after that convert it to int[]
    {
        while (true)
        {
            try
            {
                System.out.println("Please enter IP: ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String IP = bufferedReader.readLine();
                if (checkIP(IP)) return convertIP(IP);
                else throw new Exception();
            }
            catch (Exception e)
            {
                System.out.println("Invalid input - please try again.");
            }

        }
    }

    boolean checkIP(String ip) // Checking String ip format. Returns false in case not valid for IP4 format
    {
        try
        {
            if (ip == null || ip.isEmpty())
            {
                return false;
            }
            String[] parts = ip.split("\\.");
            if (parts.length != 4)
            {
                return false;
            }
            for (String s : parts)
            {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255))
                {
                    return false;
                }
            }
            if (ip.endsWith("."))
            {
                return false;
            }
            return true;
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
    }

    private int[] convertIP(String IP) //Converting String IP to int[]. No format checks in this method.
    {
        String[] IPparts = IP.split("\\.");
        int[] intIP = new int[4];
        for (int i = 0; i < intIP.length; i++)
        {
            intIP[i] = Integer.parseInt(IPparts[i]);
        }
        return intIP;
    }

    List<String> getBetweenIPs(int[] IP1, int[] IP2) //Get 2 valid int[] IP address and generate all IP between to List
    {
        List<String> result = new ArrayList<>();
        int[] smallestIP = getSmallestOrBiggestArrayIntIP(IP1,IP2,true); // This will select smallest IP
        int[] biggestIP = getSmallestOrBiggestArrayIntIP(IP1,IP2,false); // This will select biggest IP

        boolean[] matcher = new boolean[4]; // array that will show corresponding block match between 2 IP address (4 blocks total)
        for (int i = 0; i < matcher.length; i++)
        {
            if (smallestIP[i]==Math.max(IP1[i],IP2[i])) matcher[i]=true; // initial check for match.
            else break;
        }
       while (!(matcher[3] && matcher[2] && matcher[1] && matcher[0])) // all 4 blocks are match
        {
            while (!(matcher[3] && matcher[2] && matcher[1])) // last 3 blocks are match
            {
                while (!(matcher[3] && matcher[2])) // last 2 blocks are match
                {
                    while (!(matcher[3])) // block number 4 is equal
                    {
                        smallestIP[3]++;;
                        if (smallestIP[3] == biggestIP[3] && matcher[2] && matcher[1] & matcher[0])
                            matcher[3] = true;
                        if (smallestIP[3] > 255) matcher[3] = true;
                        if (!matcher[3])
                            result.add(smallestIP[0] + "." + smallestIP[1] + "." + smallestIP[2] + "." + smallestIP[3]);
                    }
                    if (!matcher[2])
                    {
                        smallestIP[2]++;
                        if (smallestIP[2] == biggestIP[2] && matcher[1] && matcher[0])
                            matcher[2] = true;
                        matcher[3] = false;
                        smallestIP[3] = -1;
                    }
                    if (smallestIP[2] > 255) matcher[2] = true;
                }
                if (!matcher[1])
                {
                    smallestIP[1]++;
                    if (smallestIP[1] == biggestIP[1] && matcher[0])
                        matcher[1] = true;
                    matcher[2] = false;
                    smallestIP[2] = -1;
                }
                if (smallestIP[1] > 255) matcher[1] = true;
            }

            if (!matcher[0])
            {
                smallestIP[0]++;
                if (smallestIP[0] == biggestIP[0])
                    matcher[0] = true;
                matcher[1] = false;
                smallestIP[1] = -1;
            }
            if (smallestIP[0] == 255) matcher[0] = true;
            if (result.size()>16000000) break;
        }
       if (result.size()>16000000)
       {
           System.out.println("Result list is too big > 16 Millions IPs. Specify more closer IPs next time.");
           System.exit(0);
       }
        return result;
    }

int[] getSmallestOrBiggestArrayIntIP(int[] IP1, int[] IP2, boolean smallTrueBigFalse) // util method used to get smallest or biggest IP depending on boolean
{
    int depth = 0;
    int mark = 0;
    while (depth<IP1.length&&mark==0)
    {
        if (IP1[depth]>IP2[depth]) mark=2;
        if (IP1[depth]<IP2[depth]) mark=1;
        depth++;
    }
    int[] result= new int[IP2.length];
        for (int i = 0; i < IP2.length; i++)
        {
            if (smallTrueBigFalse)
            {
                if (mark==2) result[i] = IP2[i];
                else result[i] = IP1[i];
            }
            else
            {
                if (mark==2) result[i] = IP1[i];
                else result[i] = IP2[i];
            }
        }
    return result;
}

}
