package t5750.springcloudalibaba.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import t5750.springcloudalibaba.model.Foo;
import t5750.springcloudalibaba.service.RocketMqSenderService;

@Configuration
public class RocketMqConfig {
	@Bean
	public CustomRunner customRunner() {
		return new CustomRunner();
	}

	@Bean
	public CustomRunnerWithTransactional customRunnerWithTransactional() {
		return new CustomRunnerWithTransactional();
	}

	public static class CustomRunner implements CommandLineRunner {
		@Autowired
		private RocketMqSenderService senderService;

		@Override
		public void run(String... args) throws Exception {
			int count = 5;
			for (int index = 1; index <= count; index++) {
				String msgContent = "msg-" + index;
				if (index % 3 == 0) {
					senderService.send(msgContent);
				} else if (index % 3 == 1) {
					senderService.sendWithTags(msgContent, "tagStr");
				} else {
					senderService.sendObject(new Foo(index, "foo"), "tagObj");
				}
			}
		}
	}

	public static class CustomRunnerWithTransactional implements
			CommandLineRunner {
		@Autowired
		private RocketMqSenderService senderService;

		@Override
		public void run(String... args) throws Exception {
			// COMMIT_MESSAGE message
			senderService.sendTransactionalMsg("transactional-msg1", 1);
			// ROLLBACK_MESSAGE message
			senderService.sendTransactionalMsg("transactional-msg2", 2);
			// COMMIT_MESSAGE message
			senderService.sendTransactionalMsg("transactional-msg3", 3);
			// COMMIT_MESSAGE message
			senderService.sendTransactionalMsg("transactional-msg4", 4);
		}
	}
}
