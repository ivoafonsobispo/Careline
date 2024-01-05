//
//  ShowMoreView.swift
//  CarelineSwifty
//
//  Created by formando on 27/11/2023.
//

import SwiftUI

struct AvailableMeasuresTextView: View {
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading) {
                Text ("WEDNESDAY, OCT 18")
                    .foregroundColor(.gray)
                    .font(.caption)
                Text("Available Measures")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            }
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 50, alignment: .leading)
        Text("Select One")
            .font(.title3)
            .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            .frame(maxWidth: .infinity,alignment: .leading)
    }
}


struct ShowMoreView: View{
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var btnBack: some View { Button(action: {
        self.presentationMode.wrappedValue.dismiss()
    }) {
        HStack {
            Image(systemName: "arrow.left")
                .resizable()
                .scaledToFit()
                .foregroundColor(.black)
                .frame(width: 15.0,height: 15.0, alignment: .leading)
            Text("Back")
        }.frame(minWidth: 0, maxWidth: .infinity, alignment: .center)
    }}
    
    var measures: [Measure]
    var token: String
    
    var body: some View {
        VStack {
            AvailableMeasuresTextView()
            ForEach(measures){measure in
                MeasureButtonView(measure: measure, token:token)
            }
            TriageButtonView(measures: measures, token:token)
        }.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .navigationBarHidden(false)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: btnBack)
    }
}
