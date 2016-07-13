package integration;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.junit5.EnableWebTesterExtensions;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;


@EnableWebTesterExtensions
@CreateBrowsersUsing(TestBrowserFactory.class)
public class BaseIntegrationTest {

}
