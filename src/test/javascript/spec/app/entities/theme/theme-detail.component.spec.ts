import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SortyquizzTestModule } from '../../../test.module';
import { ThemeDetailComponent } from 'app/entities/theme/theme-detail.component';
import { Theme } from 'app/shared/model/theme.model';

describe('Component Tests', () => {
  describe('Theme Management Detail Component', () => {
    let comp: ThemeDetailComponent;
    let fixture: ComponentFixture<ThemeDetailComponent>;
    const route = ({ data: of({ theme: new Theme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SortyquizzTestModule],
        declarations: [ThemeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ThemeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load theme on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.theme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
