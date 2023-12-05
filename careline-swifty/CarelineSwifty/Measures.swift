//
//  Measure.swift
//  CarelineSwifty
//
//  Created by formando on 16/11/2023.
//

import UIKit

class Measure: Identifiable, Decodable {
    var id = UUID()
    
    var symbol: String
    var name: String
    var metric: String
    var measured: Bool = false
    
    /**static var baseMeasures: [Measure] {
        return [
            Measure(symbol: "heart", name: "Heartbeat", data: "-1", metric: "BPM"),
            Measure(symbol: "thermometer", name: "Body Temperature", data: "-1", metric: "ºC"),
        ]
        
    }*/
    
    init(symbol: String, name: String, metric: String, measured: Bool) {
        self.symbol = symbol
        self.name = name
        self.metric = metric
        self.measured = measured
    }
}

class Temperature: Measure {
    var value: Double = -99.99
    
    init(measured: Bool, value: Double) {
        super.init(symbol: "thermometer", name: "Body Temperature", metric: "ºC", measured: measured)
        self.value = value
    }
    
    required init(from decoder: Decoder) throws {
        fatalError("init(from:) has not been implemented")
    }
}

class Heartbeat: Measure {
    var value: Int = -99
    
    init(measured: Bool, value: Int) {
        super.init(symbol: "heart", name: "Heartbeat", metric: "BPM", measured: measured)
        self.value = value
    }
    
    required init(from decoder: Decoder) throws {
        fatalError("init(from:) has not been implemented")
    }
}
