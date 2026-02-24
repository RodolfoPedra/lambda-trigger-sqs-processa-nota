package poupacompra.processanota;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class App implements RequestHandler<SQSEvent, Object> {

    public Object handleRequest(final SQSEvent event, final Context context) {
        
        event.getRecords().forEach(record -> {
            System.out.println(record.getBody());
        });

        return null;
    }
}
