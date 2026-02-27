package poupacompra.processanota;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class App implements RequestHandler<SQSEvent, Object> {

    public Object handleRequest(final SQSEvent event, final Context context) {
        
        event.getRecords().forEach(record -> {
            context.getLogger().log(String.format("Mensagem SQS %s : %s", record.getBody(), record));
        });

        return null;
    }
}
