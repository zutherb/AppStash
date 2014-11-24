package io.github.zutherb.appstash.shop.service.navigation.rest;

import io.github.zutherb.appstash.shop.service.navigation.AppServer;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import restx.tests.RestxSpecTestsRunner;
import restx.tests.FindSpecsIn;

@RunWith(RestxSpecTestsRunner.class)
@FindSpecsIn("specs/hello")
@Ignore
public class HelloResourceSpecTest {

    /**
     * Useless, thanks to both @RunWith(RestxSpecTestsRunner.class) & @FindSpecsIn()
     *
     * @Rule
     * public RestxSpecRule rule = new RestxSpecRule();
     *
     * @Test
     * public void test_spec() throws Exception {
     *     rule.runTest(specTestPath);
     * }
     */
}
