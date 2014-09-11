MobileMedia : [Sorting] [Favourites] Media [CopyMedia] [Sms] [CopyMediaOrSms] [MusicOrPhoto] [MusicAndNotPhoto] [NotMusicAndPhoto] :: _MobileMedia ;

Media : [Photo] [Music] :: _Media ;

%%

Sms or CopyMedia implies CopyMediaOrSms ;
Photo or Music implies MusicOrPhoto ;
Music and not Photo implies MusicAndNotPhoto ;
not Music and Photo implies NotMusicAndPhoto ;



