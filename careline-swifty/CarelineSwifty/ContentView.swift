//
//  ContentView.swift
//  CarelineSwifty
//
//  Created by formando on 16/11/2023.
//

import SwiftUI

struct SummaryView: View {
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading) {
                Text ("WEDNESDAY, OCT 18")
                    .foregroundColor(.gray)
                    .font(.caption)
                Text("Summary")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            }
            Text("Ivo")
                .font(.title2)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 50)
    }
}

struct LastHeartbeatView: View {
    var animation: Animation{
        Animation.easeIn(duration: 1)
            .repeatForever(autoreverses: true)
        
    }
    
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                Text("Last Heartbeat:")
                HStack{
                    Text("999")
                        .font(.custom("", fixedSize: 50))
                        .fontWeight(.bold)
                    Text("BPM")
                }.padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 0))
                Text ("WEDNESDAY, OCT 18 AT 15:35")
                    .foregroundColor(.gray)
                    .font(.caption)
            }
            Image("heartDT")
                .resizable()
                .frame(minWidth: 0, maxWidth: 100)
                .padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 0))
                .scaleEffect(1)
                .animation(animation)
        }.padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
            .frame(minWidth:  0, maxWidth: .infinity, minHeight: 0, maxHeight: 130, alignment: .topLeading)
            .background(Color("Salmon"))
            .cornerRadius(15)
    }
}

struct MeasureTextView: View {
    var body: some View {
        HStack{
            Text("Measure")
                .fontWeight(.bold)
            Button("Show More"){
                
            }.foregroundColor(.red)
                .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 30)
            .padding(EdgeInsets(top: 20, leading: 0, bottom: 0, trailing: 0))
    }
}

struct MeasureButtonsListView: View{
    var body: some View {
        List{
            MeasureButtonView(measure: Measure.sampleMeasures)
        }
    }
}

struct MeasureButtonView: View{
    var measure: Measure
    
    var body: some View {
        HStack{
            Image(systemName: measure.symbol)
            Text(measure.name)
            Text(measure.data)
        }.background(Color("Salmon"))
    }
}

struct ContentView: View {
    var body: some View {
        VStack{
            SummaryView()
            Text("Quick Status")
                .font(.title3)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: .infinity,alignment: .leading)
            LastHeartbeatView()
            MeasureTextView()
            MeasureButtonsListView()
        }.padding(EdgeInsets(top: 20, leading: 20, bottom: 40, trailing: 20))
            .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
    }
}

#Preview {
    ContentView()
}
