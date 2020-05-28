package com.hdse.shodan.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dardan.rrafshi.commons.Systems;
import com.fooock.shodan.ShodanRestApi;
import com.fooock.shodan.model.banner.Banner;
import com.fooock.shodan.model.host.Host;
import com.hdse.shodan.Constants;

import io.reactivex.observers.DisposableObserver;


public final class ShodanApiTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ShodanApiTest.class);

	public static void main(final String[] args)
	{
		final String apiKey = Systems.getEnvironmentVariable(Constants.SHODAN_API_KEY);
		final String ip = "139.9.137.182";

		final ShodanRestApi api = new ShodanRestApi(apiKey);
		api.hostByIp(ip).subscribe(new DisposableObserver<Host>() {
			@Override
			public void onNext(final Host t) {
				final String country = t.getCountryName();
				LOGGER.info(country);

				final String[] hostnames = t.getHostnames();
				LOGGER.info(Arrays.toString(hostnames));

				final int[] ports = t.getPorts();
				LOGGER.info(Arrays.toString(ports));

				final List<Banner> banners = t.getBanners();
				LOGGER.info(banners.toString());
			}

			@Override
			public void onError(final Throwable e) {
				LOGGER.error(e.getMessage(), e);
			}

			@Override
			public void onComplete() {
				LOGGER.info("Request completed");
			}
		});

		System.out.println("Press enter to exit!");

		final Scanner s = new Scanner(System.in);
		s.nextLine();
		s.close();

		System.out.println("Exiting Program...");

		System.exit(0);
	}
}
