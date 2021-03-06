package t5750.tcctransactionboot.dubbo.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * appcontext-service-tcc.xml
 */
@Configuration
@ImportResource(locations = { "classpath:tcc-transaction.xml" })
public abstract class AbstractTccConfig {
	@Value("${tcc.jdbc.url}")
	private String tccJdbcUrl;
	@Value("${c3p0.user}")
	private String jdbcUser;
	@Value("${c3p0.password}")
	private String jdbcPassword;
	@Value("${c3p0.driverClass}")
	private String jdbcDriver;
	@Value("${c3p0.initialPoolSize}")
	private int initialPoolSize;
	@Value("${c3p0.minPoolSize}")
	private int minPoolSize;
	@Value("${c3p0.maxPoolSize}")
	private int maxPoolSize;
	@Value("${c3p0.acquireIncrement}")
	private int acquireIncrement;
	@Value("${c3p0.maxIdleTime}")
	private int maxIdleTime;
	@Value("${c3p0.checkoutTimeout}")
	private int checkoutTimeout;
	@Value("${tcc.domain}")
	private String tccDomain;
	@Value("${tcc.tbSuffix}")
	private String tccTbSuffix;

	public ComboPooledDataSource createDataSource(String jdbcUrl)
			throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setDriverClass(jdbcDriver);
		dataSource.setUser(jdbcUser);
		dataSource.setPassword(jdbcPassword);
		dataSource.setInitialPoolSize(initialPoolSize);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setAcquireIncrement(acquireIncrement);
		dataSource.setMaxIdleTime(maxIdleTime);
		dataSource.setCheckoutTimeout(checkoutTimeout);
		return dataSource;
	}

	@Bean(name = "tccDataSource", destroyMethod = "close")
	@Qualifier(value = "tccDataSource")
	@Lazy(value = false)
	public DataSource tccDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = createDataSource(tccJdbcUrl);
		return dataSource;
	}

	public abstract DataSource dataSource() throws PropertyVetoException;

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager()
			throws PropertyVetoException {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
				dataSource());
		return transactionManager;
	}

	@Bean(name = "transactionRepository")
	public SpringJdbcTransactionRepository springJdbcTransactionRepository()
			throws PropertyVetoException {
		SpringJdbcTransactionRepository repository = new SpringJdbcTransactionRepository();
		repository.setDataSource(tccDataSource());
		repository.setDomain(tccDomain);
		repository.setTbSuffix(tccTbSuffix);
		return repository;
	}

	@Bean
	public DefaultRecoverConfig recoverConfig() {
		DefaultRecoverConfig recoverConfig = new DefaultRecoverConfig();
		recoverConfig.setMaxRetryCount(30);
		recoverConfig.setRecoverDuration(60);
		recoverConfig.setCronExpression("0/30 * * * * ?");
		return recoverConfig;
	}
}