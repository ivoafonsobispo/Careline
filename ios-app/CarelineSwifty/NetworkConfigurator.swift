//
//  NetworkConfiguration.swift
//  CarelineSwifty
//
//  Created by formando on 15/01/2024.
//

import Foundation

struct NetworkConfigurator {
    static func getSessionConfiguration() -> URLSessionConfiguration {
        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForResource = 30
        
        if let proxyURL = URL(string: "http://10.20.229.55/api") {
            let proxyConfiguration = [
                kCFNetworkProxiesHTTPEnable: true,
                kCFNetworkProxiesHTTPProxy: proxyURL.host!,
            ] as [CFString : Any]

            configuration.connectionProxyDictionary = proxyConfiguration as [NSObject: AnyObject]
        }

        return configuration
    }
}
