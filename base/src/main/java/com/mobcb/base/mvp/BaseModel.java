package com.mobcb.base.mvp;

import java.util.Stack;

import rx.Subscription;

/**
 * Created by lvmenghui
 * on 2018/4/8.
 */

public class BaseModel {
    private Stack<Subscription> subscriptionStack = new Stack<Subscription>();

    protected void add(Subscription subscription) {
        subscriptionStack.push(subscription);
    }

    void cancel() {
        while (subscriptionStack.size() > 0) {
            Subscription subscription = subscriptionStack.pop();
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
