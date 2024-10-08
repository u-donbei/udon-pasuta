/*
 * Copyright (c) 2024 u-donbei
 *
 * Released under the EPL license.
 *
 * see https://www.eclipse.org/legal/epl-2.0/
 */

package jp.udonbei.udonpasuta.logging.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class BelowFilter extends Filter<ILoggingEvent> {
    Level level;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }

        if (event.getLevel().isGreaterOrEqual(level)) {
            return FilterReply.DENY;
        } else {
            return FilterReply.NEUTRAL;
        }
    }

    public void setLevel(String level) {
        this.level = Level.toLevel(level);
    }

    public void start() {
        if (level != null) {
            super.start();
        }
    }
}
