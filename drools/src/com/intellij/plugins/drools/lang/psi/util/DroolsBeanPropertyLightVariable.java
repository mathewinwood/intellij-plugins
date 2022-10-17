package com.intellij.plugins.drools.lang.psi.util;

import com.intellij.psi.impl.beanProperties.BeanProperty;

public class DroolsBeanPropertyLightVariable extends DroolsLightVariable {
  private final BeanProperty myBeanProperty;

  public DroolsBeanPropertyLightVariable(BeanProperty beanProperty) {
    super(beanProperty.getName(), beanProperty.getPropertyType(), beanProperty.getPsiElement());
    myBeanProperty = beanProperty;
  }

  public BeanProperty getBeanProperty() {
    return myBeanProperty;
  }
}
