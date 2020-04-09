import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SortyquizzTestModule } from '../../../test.module';
import { ThemeScoreDetailComponent } from 'app/entities/theme-score/theme-score-detail.component';
import { ThemeScore } from 'app/shared/model/theme-score.model';

describe('Component Tests', () => {
  describe('ThemeScore Management Detail Component', () => {
    let comp: ThemeScoreDetailComponent;
    let fixture: ComponentFixture<ThemeScoreDetailComponent>;
    const route = ({ data: of({ themeScore: new ThemeScore(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SortyquizzTestModule],
        declarations: [ThemeScoreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ThemeScoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThemeScoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load themeScore on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.themeScore).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
