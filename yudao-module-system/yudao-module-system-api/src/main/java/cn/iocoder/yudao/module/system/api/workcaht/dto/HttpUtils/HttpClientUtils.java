package cn.iocoder.yudao.module.system.api.workcaht.dto.HttpUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientUtils {
    // 创建一个可复用的 HttpClient 实例
    // 您可以根据需要调整连接超时、代理等配置
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2) // 优先使用 HTTP/2
            .connectTimeout(Duration.ofSeconds(10)) // 连接超时设置为10秒
            .build();
    /**
     * 发送 HTTP GET 请求并返回响应体字符串。
     *
     * @param urlString 要请求的 URL 字符串。
     * @return 响应体字符串。
     * @throws IOException 如果发生网络错误或 I/O 错误。
     * @throws InterruptedException 如果线程在等待 HTTP 响应时被中断。
     * @throws IllegalArgumentException 如果 URL 字符串无效。
     */
    public static String get(String urlString) throws IOException, InterruptedException {
        if (urlString == null || urlString.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .timeout(Duration.ofSeconds(30)) // 单个请求超时设置为30秒
                .header("Content-Type", "application/json; charset=utf-8") // 通用请求头，可按需调整
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 检查响应状态码，您可以根据需要添加更复杂的错误处理逻辑
        // 例如，对于企业微信API，通常2xx表示成功
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            // 如果状态码不是2xx，可以抛出异常或返回包含错误信息的特定格式
            // 这里简单地抛出一个包含状态码和响应体的IOException
            throw new IOException("HTTP GET request failed with status code " + response.statusCode() +
                    ", response body: " + response.body());
        }
    }

    /**
     * 发送 HTTP POST 请求并返回响应体字符串。
     *
     * @param urlString   要请求的 URL 字符串。
     * @param jsonPayload 要在请求体中发送的 JSON 字符串。
     * @return 响应体字符串。
     * @throws IOException 如果发生网络错误或 I/O 错误。
     * @throws InterruptedException 如果线程在等待 HTTP 响应时被中断。
     * @throws IllegalArgumentException 如果 URL 字符串或 JSON payload 无效。
     */
    public static String post(String urlString, String jsonPayload) throws IOException, InterruptedException {
        if (urlString == null || urlString.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty.");
        }
        if (jsonPayload == null) { // 空的JSON payload "" 是允许的，但null不行
            throw new IllegalArgumentException("JSON payload cannot be null.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .timeout(Duration.ofSeconds(30)) // 单个请求超时设置为30秒
                .header("Content-Type", "application/json; charset=utf-8") // POST请求通常需要指定内容类型为JSON
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload)) // 设置请求体
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 检查响应状态码
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            throw new IOException("HTTP POST request failed with status code " + response.statusCode() +
                    ", request payload: " + jsonPayload +
                    ", response body: " + response.body());
        }
    }

}
