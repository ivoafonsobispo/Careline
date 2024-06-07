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
    var measured: Bool
    
    init(symbol: String = "", name: String = "", metric: String = "", measured: Bool = false) {
        self.symbol = symbol
        self.name = name
        self.metric = metric
        self.measured = measured
    }
}

class Temperature: Measure {
    var value: Double = -99.99
    
    init(measured: Bool, value: Double) {
        super.init(symbol: "thermometer", name: "Temperature", metric: "ºC", measured: measured)
        self.value = value
    }
    
    private enum CodingKeys: String, CodingKey {
        case value
    }
    
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.value = try container.decode(Double.self, forKey: .value)
        try super.init(from: decoder)
    }
}

class Heartbeat: Measure {
    var value: Int = -99
    
    init(measured: Bool, value: Int) {
        super.init(symbol: "heart", name: "Heartbeat", metric: "BPM", measured: measured)
        self.value = value
    }
    
    private enum CodingKeys: String, CodingKey {
        case value, symbol, name, metric, measured
    }
    
    required init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.value = try container.decode(Int.self, forKey: .value)
        try super.init(from: decoder)
    }
}
