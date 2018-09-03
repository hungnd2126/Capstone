using AutoMapper;

namespace APIProject.Mappings
{

    public class ViewModelToDomainMappingProfile : Profile
    {
        
        public override string ProfileName
        {
            get { return "ViewModelToDomainMappings"; }
        }
        public ViewModelToDomainMappingProfile()
        {
            //Mapper.CreateMap<UserFormModel, User>();
            //Mapper.CreateMap<UserFormViewModel, User>().ForMember(x => x.Id, opt => opt.MapFrom(source => source.UserId));
            //Mapper.CreateMap<XViewModel, X()
            //    .ForMember(x => x.PropertyXYZ, opt => opt.MapFrom(source => source.Property1));     
            //Mapper.CreateMap<BlogFormModel, Blog>();
        }
    }
}