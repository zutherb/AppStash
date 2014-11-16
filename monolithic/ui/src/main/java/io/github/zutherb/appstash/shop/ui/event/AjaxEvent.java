package io.github.appstash.shop.ui.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface AjaxEvent {
    AjaxRequestTarget getTarget();
}
