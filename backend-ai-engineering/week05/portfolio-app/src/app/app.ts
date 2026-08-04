import { Component, signal, AfterViewInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements AfterViewInit {
  protected readonly title = signal('portfolio-app');

  ngAfterViewInit() {
    this.loadScript('assets/js/gallery-init.js');
    this.loadScript('assets/js/app.js');
  }

  private loadScript(url: string) {
    const script = document.createElement('script');
    script.src = url;
    script.async = true;
    document.body.appendChild(script);
  }
}
