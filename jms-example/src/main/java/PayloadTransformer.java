import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

public class PayloadTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		
		String payloadAsString = (String)message.getPayload();
		
		String[] rows = payloadAsString.split("\n");
		
		String filename = rows[1];
		String fileType = rows[2];
		
		payloadAsString = payloadAsString.replace(filename, "");
		payloadAsString = payloadAsString.replace(fileType, "");
		payloadAsString = payloadAsString.replace(rows[0], "");
		payloadAsString = payloadAsString.replace(rows[rows.length -1], "");
		
		filename = filename.split(";")[2].split("=")[1].replace("\"", "");
		
		message.setProperty("filename",filename, PropertyScope.SESSION);
		message.setProperty("filetype", filename.split("\\.")[1], PropertyScope.SESSION);
		
		message.setPayload(payloadAsString);
		
		return message;
	}
	
	

}
