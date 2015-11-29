package wcresearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class WCResearcher {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.append(String.valueOf(args.length)).println();
        if (args.length < 2) {
            System.out.append("<url> <path_to_log_files>").println();
            System.exit(-1);
        }

        URI uri = URI.create(args[0]);
        String str = args[1];

        File directory = new File(str);
        for (File file : directory.listFiles()) {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String arg;
            StringBuilder sb = new StringBuilder("[");
            while ((arg = br.readLine()) != null) {
                sb.append(arg);
            }
            sb.append("]");

            HttpPost request = new HttpPost(uri);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpEntity he = new StringEntity(sb.toString(), ContentType.APPLICATION_JSON);
            request.setEntity(he);
            request.addHeader("Accept", "application/json");
            request.addHeader("Cache-Control", "no-cache");

            HttpResponse response = httpClient.execute(request);

            HttpEntity re = response.getEntity();

            for (Header h : response.getAllHeaders()) {
                System.out.append(String.format("%s %s", h.getName(), h.getValue())).println();
            }
            System.out.println();

            //System.out.append(sb.toString()).println();
            //break;
            //file.delete();
        }
    }
}
