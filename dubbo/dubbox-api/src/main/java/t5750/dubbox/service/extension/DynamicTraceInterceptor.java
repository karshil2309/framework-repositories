package t5750.dubbox.service.extension;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

@Priority(Priorities.USER)
public class DynamicTraceInterceptor
		implements ReaderInterceptor, WriterInterceptor {
	public Object aroundReadFrom(
			ReaderInterceptorContext readerInterceptorContext)
			throws IOException, WebApplicationException {
		System.out.println("Dynamic reader interceptor invoked");
		return readerInterceptorContext.proceed();
	}

	public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
			throws IOException, WebApplicationException {
		System.out.println("Dynamic writer interceptor invoked");
		writerInterceptorContext.proceed();
	}
}
