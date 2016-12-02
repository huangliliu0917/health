import com.huotu.health.entity.support.TemplageIdConverter;
import com.huotu.health.entity.support.TemplateId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class OtherTest {
    @Test
    public void test(){
        TemplageIdConverter converter=new TemplageIdConverter();
        List<TemplateId> templateIds=new ArrayList<>();

        templateIds.add(new TemplateId(1));
        templateIds.add(new TemplateId(2));
        templateIds.add(new TemplateId(3));

        System.out.println(converter.convertToDatabaseColumn(templateIds));
    }
}
