/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


package jdk.internal.net.http.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface AltSvcProcess {

    /**
     * Process Alt-Svc header values, some of the header
     * values are filtered.Processed values are temporarily
     * kept as Map, once the registry is complete.
     * Alt-Svc registry would be used
     * @param headerVals
     */
    static Map<String, String> mapAltSvcHeaders(List<String> headerVals) {
        var headerMap = new HashMap<String, String>();
        for(String keyVal : filterHeaders(headerVals)) {
             String[] vals = keyVal.split(":");
             String key = vals[0] == null || vals[0].isEmpty() ? "empty" : vals[0]; // "null"will change as api evolves
             String val = vals[1] == null || vals[1].isEmpty() ? "empty" : vals[1]; // "null"will change as api evolves
             headerMap.put(key, val);
        }
        return headerMap;
    }

    /**
     * Filter values on different criteria.
     * Bound to be modified as the the filter
     * criteria evolve
     * @param headerVals
     * @return
     */
    static List<String> filterHeaders(List<String> headerVals) {
        return headerVals.parallelStream()
                         .filter( vals -> vals.startsWith("h2") || vals.startsWith("h3"))
                         .collect(Collectors.toList());
    }
}
