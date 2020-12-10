package com.sample.config.aware;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author lidengjin
 * @Date 2020/12/9 6:03 下午
 * @Version 1.0
 */
public class MapperInstantiationAwareBeanProcessor extends BeanNameAutoProxyCreator
		implements BeanFactoryPostProcessor {

	private List<String> MAPPER_BEAN_LIST = new CopyOnWriteArrayList();

	private boolean initDone = false;

	public MapperInstantiationAwareBeanProcessor() {
		this.setProxyTargetClass(true);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory defaultBeanFactory = (DefaultListableBeanFactory) beanFactory;
		String[] beanDefinitionNames = defaultBeanFactory.getBeanDefinitionNames();
		String[] beanDefinitionNamesArray = beanDefinitionNames;
		int beanDefinitionNamesLength = beanDefinitionNames.length;

		for (int i = 0; i < beanDefinitionNamesLength; ++i) {
			String beanName = beanDefinitionNamesArray[i];
			BeanDefinition beanDefinition = defaultBeanFactory.getBeanDefinition(beanName);
			if (beanDefinition.getBeanClassName() != null && beanDefinition.getBeanClassName()
					.equals("org.mybatis.spring.mapper.MapperFactoryBean")) {
				this.MAPPER_BEAN_LIST.add(beanName);
			}
		}
	}

	public void initBeanNames() {
		if (!this.initDone) {
			if (this.MAPPER_BEAN_LIST.size() != 0) {
				this.setBeanNames((String[]) this.MAPPER_BEAN_LIST.toArray(new String[0]));
			} else {
				this.setBeanNames(new String[]{""});
			}

			this.initDone = true;
		}

	}

	@Override
	protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource targetSource) {
		this.initBeanNames();
		return super.getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
	}
}
