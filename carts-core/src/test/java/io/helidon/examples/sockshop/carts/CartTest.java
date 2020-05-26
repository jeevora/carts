/*
 *  Copyright (c) 2020 Oracle and/or its affiliates.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.helidon.examples.sockshop.carts;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for Cart class.
 */
class CartTest {
    @Test
    void testCartCreation() {
        Cart cart = new Cart("123");
        assertThat(cart.getCustomerId(), is("123"));
        assertThat(cart.getItems(), is(empty()));
    }

    @Test
    void testItemAddition() {
        Cart cart = new Cart("123");
        Item x1 = new Item("X1", 5, 10.0f);
        Item x2 = new Item("X2", 3, 5.99f);

        cart.add(x1);
        cart.add(x2);

        assertThat(cart.getItems(), hasSize(2));
        assertThat(cart.getItem("X1"), is(x1));
        assertThat(cart.getItem("X1").getCart(), is(cart));
        assertThat(cart.getItem("X2"), is(x2));
        assertThat(cart.getItem("X2").getCart(), is(cart));

        cart.add(x1);
        assertThat(cart.getItems(), hasSize(2));
        assertThat(cart.getItem("X1").getQuantity(), is(10));
    }

    @Test
    void testItemUpdate() {
        Cart cart = new Cart("123");
        Item x1 = new Item("X1", 5, 10.0f);
        Item x2 = new Item("X2", 3, 5.99f);

        cart.update(x1);
        cart.update(x2);

        assertThat(cart.getItems(), hasSize(2));
        assertThat(cart.getItem("X1"), is(x1));
        assertThat(cart.getItem("X1").getCart(), is(cart));
        assertThat(cart.getItem("X2"), is(x2));
        assertThat(cart.getItem("X2").getCart(), is(cart));

        cart.update(x1);
        assertThat(cart.getItems(), hasSize(2));
        assertThat(cart.getItem("X1").getQuantity(), is(5));
    }

    @Test
    void testItemRemoval() {
        Cart cart = new Cart("123");
        Item x1 = new Item("X1", 5, 10.0f);
        Item x2 = new Item("X2", 3, 5.99f);

        cart.add(x1);
        cart.add(x2);

        assertThat(cart.getItems(), hasSize(2));

        cart.remove("X1");
        assertThat(cart.getItems(), hasSize(1));
        assertThat(cart.getItem("X1"), nullValue());
    }

    @Test
    void testCartMerge() {
        Cart source = new Cart("456");
        Item s1 = new Item("X1", 5, 10f);
        Item s2 = new Item("X2", 3, 5.99f);
        source.add(s1);
        source.add(s2);

        Cart cart = new Cart("123");
        Item t1 = new Item("X2", 3, 5.99f);
        Item t2 = new Item("X3", 1, 20f);
        cart.add(t1);
        cart.add(t2);

        cart.merge(source);
        assertThat(cart.getItems(), hasSize(3));
        assertThat(cart.getItem("X1"), is(s1));
        assertThat(cart.getItem("X2").getQuantity(), is(6));
        assertThat(cart.getItem("X3"), is(t2));
    }
}
