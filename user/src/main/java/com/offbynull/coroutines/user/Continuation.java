package com.offbynull.coroutines.user;

import java.util.Deque;
import java.util.LinkedList;
import org.apache.commons.lang3.Validate;

public final class Continuation {
    public static final int MODE_STARTING = 0;
    public static final int MODE_SAVING = 1;
    public static final int MODE_LOADING = 2;
    private Deque<MethodState> methodStates = new LinkedList<>();
    private int mode = MODE_STARTING;

    Continuation() {
        // do nothing
    }
    
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        Validate.isTrue(mode == MODE_STARTING || mode == MODE_SAVING || mode == MODE_LOADING);
        this.mode = mode;
    }
    
    public void push(MethodState methodState) {
        Validate.notNull(methodState);
        methodStates.push(methodState);
    }
    
    public MethodState pop() {
        Validate.validState(!methodStates.isEmpty());
        return methodStates.pop();
    }

    public MethodState peek() {
        Validate.validState(!methodStates.isEmpty());
        return methodStates.peek();
    }

    public void suspend() {
        throw new UnsupportedOperationException("Caller not instrumented");
    }
    
    public static final class MethodState {
        private final int continuationPoint;
        private final Object[] stack;
        private final Object[] localTable;

        public MethodState(int continuationPoint, Object[] stack, Object[] localTable) {
            Validate.isTrue(continuationPoint >= 0);
            Validate.notNull(stack);
            Validate.notNull(localTable);
            this.continuationPoint = continuationPoint;
            this.stack = stack.clone();
            this.localTable = localTable.clone();
        }

        public int getContinuationPoint() {
            return continuationPoint;
        }

        public Object[] getStack() {
            return stack.clone();
        }

        public Object[] getLocalTable() {
            return localTable.clone();
        }
        
    }
}