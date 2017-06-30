package ru.bars_open.medvtr.soap.ws.lis.across.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Admin on 30.06.2017.
 */
public class LoggingContextFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger("ACCESS");

    private static final AtomicLong counter = new AtomicLong();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final long startTime = System.nanoTime();
        final String url = request.getRequestURL().append(request.getQueryString() != null ? "?" + request.getQueryString() : "").toString();
        MDC.clear();
        MDC.put("requestNumber", String.valueOf(counter.incrementAndGet()));
        MDC.put("sessionId", request.getSession().getId());
        MDC.put("method", request.getMethod());
        MDC.put("URL", url );
        chain.doFilter(request, resp);
        LOGGER.info("[{}][{}] end in [{}] ms.", request.getMethod(), url, (System.nanoTime() - startTime) / 1000000);
    }

    @Override
    public void destroy() {
    }
}
