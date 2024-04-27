# Sample Project - Webflux Holy Quran API

Webflux Holy Quran is a sample Spring WebFlux application that demonstrates how to build a reactive web service using the WebFlux framework. It includes integration with an external API, configuration management, and error handling.

## Purpose

The purpose of this project is to showcase the implementation of a reactive web service using Spring WebFlux. It integrates with an external Quran API to retrieve information about Quranic chapters (Surahs) and demonstrates how to handle errors and exceptions in a reactive environment.

## Version

Current version: 1.0

## Application Properties

The application is configured using the `application.yml` file. Below are the key properties:

```yaml
api:
  quranapi:
    host: https://api.alquran.cloud
    truststore:
      ciphers:
        - TLS_AES_128_GCM_SHA256
        - TLS_AES_256_GCM_SHA384
        - TLS_CHACHA20_POLY1305_SHA256
      certificates: |
        -----BEGIN CERTIFICATE-----
        MIIEYDCCAkigAwIBAgIQB55JKIY3b9QISMI/xjHkYzANBgkqhkiG9w0BAQsFADBP
        ...
        -----END CERTIFICATE-----
  connection:
    keepAlive: true
    tcpNoDelay: true
    connectTimeoutSeconds: 10
    readTimeoutSeconds: 50
    writeTimeoutSeconds: 50
```
## Configuration Properties

The application is configured using the `application.yml` file. Below are the key properties:

<table>
  <tr>
    <th>Property</th>
    <th>Description</th>
  </tr>

  <tr>
    <td><code>api.quranapi.host</code></td>
    <td>The base URL of the external Quran API. This property specifies the endpoint where the application communicates with the Quran API to retrieve information about Quranic chapters (Surahs).</td>
  </tr>

  <tr>
    <td><code>api.quranapi.truststore.ciphers</code></td>
    <td>List of accepted ciphers for secure communication. Ciphers are cryptographic algorithms used to secure the communication between the application and the Quran API. This property defines the allowed ciphers for encryption.</td>
  </tr>

  <tr>
    <td><code>api.quranapi.truststore.certificates</code></td>
    <td>Concatenated PEM certificates for truststore. Truststore is a repository of certificates that the application trusts. This property contains the concatenated PEM certificates used to establish a secure connection with the Quran API.</td>
  </tr>

  <tr>
    <td><code>api.quranapi.connection.keepAlive</code></td>
    <td>Boolean indicating whether to use keep-alive. Keep-alive is a mechanism that allows the application to reuse a TCP connection for multiple requests, reducing the overhead of establishing a new connection for each request. This property determines whether keep-alive is enabled (true) or disabled (false).</td>
  </tr>

  <tr>
    <td><code>api.quranapi.connection.tcpNoDelay</code></td>
    <td>Boolean indicating whether to use TCP no delay. TCP no delay is a setting that, when enabled, minimizes the delay in sending small packets. This property determines whether TCP no delay is enabled (true) or disabled (false).</td>
  </tr>

  <tr>
    <td><code>api.quranapi.connection.connectTimeoutSeconds</code></td>
    <td>Connect timeout in seconds. This property sets the maximum time the application waits to establish a connection with the Quran API. If a connection cannot be established within this time, a timeout exception is thrown.</td>
  </tr>

  <tr>
    <td><code>api.quranapi.connection.readTimeoutSeconds</code></td>
    <td>Read timeout in seconds. This property sets the maximum time the application waits for a response from the Quran API after the connection is established. If no response is received within this time, a read timeout exception is thrown.</td>
  </tr>

  <tr>
    <td><code>api.quranapi.connection.writeTimeoutSeconds</code></td>
    <td>Write timeout in seconds. This property sets the maximum time the application waits to send data to the Quran API. If the data cannot be sent within this time, a write timeout exception is thrown.</td>
  </tr>
</table>


These properties collectively define the communication behavior and security parameters between the Webflux Holy Quran application and the external Quran API. Adjusting these values allows for fine-tuning the application's interaction with the external API based on specific requirements and constraints.

## Getting Started

To run the application locally, follow these steps:

### Prerequisites

- Java 17 or later
- Maven

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/webflux-holy-quran.git
    cd webflux-holy-quran
    ```

2. Build and run the application:

    ```bash
    mvn spring-boot:run
    ```

    The application will be accessible at [http://localhost:8080](http://localhost:8080).

## Usage

Explore the API by accessing the provided endpoints. For example:

- Retrieve information about a specific chapter:

    ```bash
    curl http://localhost:8080/v1/surah/1/en.asad
    ```

**Performance Testing of Holy Quran API**
=====================================

**Overview**
------------

This document outlines the performance testing strategy and configuration for the Holy Quran API. The API is built using Spring Boot and uses WireMock for mocking backend services. The performance testing is done using Docker containers,Grafana, K6 and Prometheus for monitoring.

**Goals**
---------------------------

* Measure the response time and throughput of the API under various loads
* Identify bottlenecks and optimize the API for better performance
* Ensure the API can handle a large number of concurrent requests

**Tools**
---------------------------

* **Docker**: Used to containerize the API and WireMock services
* **Prometheus**: Used for monitoring and collecting metrics
* **Grafana**: Used for visualizing metrics and dashboards
* **K6**: Used for making http requests to api for performance testing

**Configuration**
-----------------------------------

The performance testing configuration is defined in the `docker-compose.yml` file. The configuration includes:

* **holyquranapi**: The API service built using Spring Boot
* **docker-wiremock**: The WireMock service used for mocking backend services
* **prometheus**: The Prometheus service used for monitoring and collecting metrics
* **grafana**: The Grafana service used for visualizing metrics and dashboards


**Running Performance Tests**
---------------------------

To run the performance tests, follow these steps:

1. Start the Docker containers using `docker-compose up`
2. Run the performance testing tool K6 using the command ```k6 run load-test.js ```  being in project root. Before starting k6 test, we should have k6 install.Follow k6 installation steps for more details.
3. Monitor the metrics using Prometheus and Grafana
4. Analyze the results and identify bottlenecks and areas for optimization

**Optimization and Tuning**
-------------------------

Based on the performance testing results, the following optimizations and tunings can be made:

* **Tuning JVM settings**: Adjusting JVM settings such as heap size, garbage collection, and thread pool size
* **Optimizing database queries**: Optimizing database queries to reduce latency and improve throughput
* **Caching**: Implementing caching mechanisms to reduce the load on the API
* **Load balancing**: Implementing load balancing to distribute the load across multiple instances of the API

By following this performance testing strategy and configuration, we can ensure that the Holy Quran API is optimized for better performance and can handle a large number of concurrent requests.


**Install K6 on Windows, Linux, and macOS**
-------------------------------------------------------

**Windows**

1. Download the K6 installer for Windows from the official K6 website: <https://k6.io/docs/get-started/installation/>
2. Run the installer and follow the prompts to install K6.
3. Once the installation is complete, open a new command prompt or PowerShell window.
4. Verify that K6 is installed by running the command `k6 version`.

**Linux (Fedora/CentOS)**

1. Open a terminal window and run the following commands:
```
sudo dnf install https://dl.k6.io/rpm/repo.rpm
sudo dnf install k6
```
2. Once the installation is complete, verify that K6 is installed by running the command `k6 version`.

**Linux (Debian/Ubuntu)**

1. Open a terminal window and run the following commands:
```
sudo gpg -k
sudo gpg --no-default-keyring --keyring /usr/share/keyrings/k6-archive-keyring.gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys C5AD17C747E3415A3642D57D77C6C491D6AC1D69
echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
sudo apt-get update
sudo apt-get install k6
```
2. Once the installation is complete, verify that K6 is installed by running the command `k6 version`.


**macOS (using Homebrew)**

1. Open a terminal window and run the following command to install K6 using Homebrew:
```
brew install k6
```

**Note**: You can always follow the updated installation guide in following link <https://k6.io/docs/get-started/installation/>

That's it! You should now have K6 installed on your system.

## Contributing

Feel free to contribute by submitting issues or pull requests.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- Special thanks to [Quran API](https://api.alquran.cloud) for providing the external API.
