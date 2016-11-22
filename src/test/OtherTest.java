import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class OtherTest {
    @Test
    public void test(){
        StringBuilder templatesName=new StringBuilder("");

        List<String> s=new ArrayList<>();
        s.add("abc");
        s.add("def");
        s.add("awef");

        Iterator<String> ite=s.iterator();
        while (ite.hasNext()){
            String template=ite.next();
            templatesName.append(template);
            if(ite.hasNext()){
                templatesName.append(",");
            }
        }

        System.out.println(templatesName.toString());
    }
}
