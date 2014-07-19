/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.glowroot.sandbox.ui;

/**
 * @author Trask Stalnaker
 * @since 0.5
 */
class NestableCall {

    private final NestableCall child;
    private final int numExpensiveCalls;
    private final int maxTimeMillis;
    private final int maxSpanMessageLength;

    NestableCall() {
        this(0, 0, 0);
    }

    NestableCall(int numExpensiveCalls, int maxTimeMillis, int maxSpanMessageLength) {
        this.child = null;
        this.numExpensiveCalls = numExpensiveCalls;
        this.maxTimeMillis = maxTimeMillis;
        this.maxSpanMessageLength = maxSpanMessageLength;
    }

    NestableCall(NestableCall child, int numExpensiveCalls, int maxTimeMillis,
            int maxSpanMessageLength) {
        this.child = child;
        this.numExpensiveCalls = numExpensiveCalls;
        this.maxTimeMillis = maxTimeMillis;
        this.maxSpanMessageLength = maxSpanMessageLength;
    }

    void execute() {
        if (child != null) {
            child.execute();
        }
        ExpensiveCall expensiveCall = new ExpensiveCall(maxTimeMillis, maxSpanMessageLength);
        for (int i = 0; i < numExpensiveCalls; i++) {
            expensiveCall.execute();
        }
    }
}