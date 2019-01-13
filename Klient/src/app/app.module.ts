import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu/menu.component';
import { MaterialModule } from './material.module';
import { ScenarioComponent } from './components/scenario/scenario.component';
import { HttpHelper } from './utils/HttpHelper.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DepthScenarioComponent } from './components/depth-scenario/depth-scenario.component';
import { StepCountComponent } from './components/step-count/step-count.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    ScenarioComponent,
    DepthScenarioComponent,
    StepCountComponent
  ],
  imports: [
    BrowserModule,
    MaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [HttpHelper],
  bootstrap: [AppComponent]
})
export class AppModule { }
