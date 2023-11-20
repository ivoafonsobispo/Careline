//
//  TriageView.swift
//  CarelineSwifty
//
//  Created by formando on 20/11/2023.
//

import SwiftUI

struct TriageView: View{
    var measures: [Measure]
    
    var body: some View {
        ForEach(measures){measure in
            MeasureButtonView(measure: measure)
        }
    }
}
