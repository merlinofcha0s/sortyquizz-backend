import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SortyquizzTestModule } from '../../../test.module';
import { UserPackUpdateComponent } from 'app/entities/user-pack/user-pack-update.component';
import { UserPackService } from 'app/entities/user-pack/user-pack.service';
import { UserPack } from 'app/shared/model/user-pack.model';

describe('Component Tests', () => {
  describe('UserPack Management Update Component', () => {
    let comp: UserPackUpdateComponent;
    let fixture: ComponentFixture<UserPackUpdateComponent>;
    let service: UserPackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SortyquizzTestModule],
        declarations: [UserPackUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserPackUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserPackUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserPackService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserPack(123);
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
        const entity = new UserPack();
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
