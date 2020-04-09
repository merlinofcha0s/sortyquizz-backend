import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SortyquizzTestModule } from '../../../test.module';
import { ThemeScoreUpdateComponent } from 'app/entities/theme-score/theme-score-update.component';
import { ThemeScoreService } from 'app/entities/theme-score/theme-score.service';
import { ThemeScore } from 'app/shared/model/theme-score.model';

describe('Component Tests', () => {
  describe('ThemeScore Management Update Component', () => {
    let comp: ThemeScoreUpdateComponent;
    let fixture: ComponentFixture<ThemeScoreUpdateComponent>;
    let service: ThemeScoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SortyquizzTestModule],
        declarations: [ThemeScoreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ThemeScoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeScoreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeScoreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ThemeScore(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ThemeScore();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
