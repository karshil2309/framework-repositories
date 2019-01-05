package t5750.pay.service.notify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @描述: 启动Dubbo服务用的MainClass.
 * @版本: 1.0 .
 */
public class PayServiceNotifyDubboProvider {
	private static final Log log = LogFactory
			.getLog(PayServiceNotifyDubboProvider.class);

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("== DubboProvider context start error:", e);
		}
		synchronized (PayServiceNotifyDubboProvider.class) {
			while (true) {
				try {
					PayServiceNotifyDubboProvider.class.wait();
				} catch (InterruptedException e) {
					log.error("== synchronized error:", e);
				}
			}
		}
	}
}