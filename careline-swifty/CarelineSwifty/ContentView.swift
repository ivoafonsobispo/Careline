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
    @State private var isAnimating = false
    
    var animation: Animation {
        Animation.easeInOut(duration: 0.6) // Mudar o ritmo da animaçâo aqui
            .repeatForever(autoreverses: true)
    }
    
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                Text("Last Heartbeat:")
                HStack{
                    Text("70")
                        .font(.custom("", fixedSize: 50))
                        .fontWeight(.bold)
                    Text("BPM")
                }.padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 20))
                Text ("WEDNESDAY, OCT 18 AT 15:35")
                    .foregroundColor(.gray)
                    .font(.caption)
            }
            Image("heartDT")
                .resizable()
                .scaleEffect(isAnimating ? 0.8 : 1.0)
                .frame(minWidth: 100, maxWidth: 100, minHeight: 90, maxHeight: 90)
                .onAppear{
                    DispatchQueue.main.async {
                        withAnimation(animation) {
                            isAnimating = true
                        }
                    }
                }

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
    var measures = Measure.baseMeasures
    
    var body: some View {
        ForEach(measures){measure in
            MeasureButtonView(measure: measure)
        }
        TriageButtonView(measures: measures)
    }
}

struct TriageButtonView: View{
    var measures: [Measure]
    
    var body: some View {
        NavigationLink(destination: TriageView(measures: measures)) {
            HStack{
                Image(systemName: "checkmark.square")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 30.0,height: 30.0)
                Text("Triage")
                    .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 0))
                Text("RIGHT NOW >")
                    .foregroundColor(Color.gray)
                    .font(.caption)
                    .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 30, alignment: .leading)
                .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
                .foregroundColor(Color.black)
        }.background(Color("Salmon"))
            .padding(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
            .cornerRadius(15)
            
    }
}

struct MeasureButtonView: View{
    var measure: Measure
    
    var body: some View {
        NavigationLink(destination: MeasureView(measure: measure)) {
            HStack{
                Image(systemName: measure.symbol)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 30.0,height: 30.0)
                Text(measure.name)
                    .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 0))
                Text("RIGHT NOW >")
                    .foregroundColor(Color.gray)
                    .font(.caption)
                    .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 30, alignment: .leading)
                .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
                .foregroundColor(Color.black)
        }.background(Color("Salmon"))
            .padding(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
            .cornerRadius(15)
            
    }
}

struct ContentView: View {
    var body: some View {
        NavigationView{
            VStack{
                SummaryView()
                Text("Quick Status")
                    .font(.title3)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                    .frame(maxWidth: .infinity,alignment: .leading)
                LastHeartbeatView()
                MeasureTextView()
                MeasureButtonsListView()
            }
                .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
                .navigationBarHidden(true)
                .navigationBarTitle("", displayMode: .inline)
        }.padding(EdgeInsets(top: 30, leading: 20, bottom: 40, trailing: 20))
            .foregroundColor(.black)
    }
}

#Preview {
    ContentView()
}

