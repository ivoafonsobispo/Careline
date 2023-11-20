//
//  MeasureView.swift
//  CarelineSwifty
//
//  Created by formando on 17/11/2023.
//

import SwiftUI

struct MeasuringTextView: View {
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading) {
                Text ("WEDNESDAY, OCT 18")
                    .foregroundColor(.gray)
                    .font(.caption)
                Text("Measuring")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            }
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 50, alignment: .leading)
    }
}

struct MeasureValueView: View {
    var measure: Measure
    
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                Text("Reading:")
                HStack{
                    Text(measure.data)
                        .font(.custom("", fixedSize: 50))
                        .fontWeight(.bold)
                    Text(measure.metric)
                }.padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 20))
                Text ("WEDNESDAY, OCT 18 AT 15:35")
                    .foregroundColor(.gray)
                    .font(.caption)
            }
            Image(systemName: measure.symbol)
                .resizable()
                .scaledToFit()
                .frame(width: 75.0, height: 75.0)
        }.padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
            .frame(minWidth:  0, maxWidth: .infinity, minHeight: 0, maxHeight: 130, alignment: .topLeading)
            .background(Color("Salmon"))
            .cornerRadius(15)
    }
}

struct MeasureView: View{
    var measure: Measure
    
    var body: some View {
        VStack{
            MeasuringTextView()
            Text(measure.name)
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: .infinity,alignment: .leading)
            MeasureValueView(measure: measure)
        }
            .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .navigationBarHidden(false)
            .navigationTitle("Back")
    }
}
